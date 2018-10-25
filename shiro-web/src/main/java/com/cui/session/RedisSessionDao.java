package com.cui.session;

import com.cui.controller.util.JedisUtil;
import com.sun.xml.internal.ws.developer.Serialization;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: cuijq
 */
public class RedisSessionDao extends AbstractSessionDAO {
    private final String SHIRO_SESSION_PREFIX = "shiro-session";

    public byte[] getKey(String key){
        return (SHIRO_SESSION_PREFIX+key).getBytes();
    }

    public void saveSession(Session session){
        if(session != null && session.getId() != null){
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key,value);
            jedisUtil.expire(key,600);
        }
    }

    @Resource
    private JedisUtil jedisUtil;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId =   generateSessionId(session);
        //将sessionid和session捆绑
        assignSessionId(session,sessionId);

        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        Session session = (Session)SerializationUtils.deserialize(value);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null) {
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisUtil.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<>();
        if(CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for (byte[] key : keys){
            Session session =  (Session)SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }

        return sessions;
    }
}
