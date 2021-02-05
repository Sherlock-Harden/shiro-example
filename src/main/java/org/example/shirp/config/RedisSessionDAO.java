package org.example.shirp.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/13
 **/
@Slf4j

public class RedisSessionDAO extends AbstractSessionDAO {

  private static final String KEY_PREFIX = "shiro_session:";
  private static final long DEFAULT_SESSION_TIMEOUT = 1800000L;
  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Override

  protected Serializable doCreate(Session session) {
    Serializable sessionId = this.generateSessionId(session);
    this.assignSessionId(session, sessionId);
    this.saveSession(session);
    return sessionId;
  }

  @Override
  protected Session doReadSession(Serializable sessionId) {
    if (sessionId == null) {
      log.error("session id is null");
      return null;
    }
    return (Session) redisTemplate.opsForValue().get(KEY_PREFIX + sessionId);
  }

  @Override
  public void update(Session session) throws UnknownSessionException {
    this.saveSession(session);
  }

  @Override
  public void delete(Session session) {
    if (session == null || session.getId() == null) {
      log.error("session or session id is null");
      return;
    }
    redisTemplate.delete(KEY_PREFIX + session.getId());
  }

  @Override
  public Collection<Session> getActiveSessions() {
    Set<Session> sessions = new HashSet<>();
    Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");
    if (keys != null && !keys.isEmpty()) {
      for (String key : keys) {
        Session session = (Session) redisTemplate.opsForValue().get(KEY_PREFIX + key);
        sessions.add(session);
      }
    }
    return sessions;
  }

  private void saveSession(Session session) throws UnknownSessionException {
    if (session == null || session.getId() == null) {
      log.error("session or session id is null");
      return;
    }
    session.setTimeout(DEFAULT_SESSION_TIMEOUT);
    redisTemplate.opsForValue()
        .set(KEY_PREFIX + session.getId(), session, DEFAULT_SESSION_TIMEOUT, TimeUnit.MILLISECONDS);
  }
}
