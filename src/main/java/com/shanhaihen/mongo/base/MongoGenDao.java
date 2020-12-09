package com.shanhaihen.mongo.base;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public abstract class MongoGenDao<T>{
    private final static Logger logger= Logger.getLogger(MongoGenDao.class);
    @Autowired
    protected MongoTemplate mongoTemplate;
    /**
     * 为属性自动注入bean服务
     * @param mongoTemplate
     */
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    /**
     * 保存一个对象
     * @param t
     */
    public void save(T t){
        this.mongoTemplate.save(t);
    }
    /**
     * 添加一个对象
     * @param t
     */
    public void insert(T t){
        this.mongoTemplate.insert(t);
    }
    
    public void insertJson(String collection, String json) {
    	this.mongoTemplate.insert(json, collection);
    }

    public void insertAll(List<T> list){
        this.mongoTemplate.insertAll(list);
    }
    /**
     * 根据id获取
     * @param id
     * @return
     */
    public T queryById(String id){
        Query query=new Query();
        Criteria criteria= Criteria.where("_id").is(id);
        query.addCriteria(criteria);
        return (T) this.mongoTemplate.findOne(query, getEntityClass());
    }
    /**
     * 根据条件查询集合
     * @param query
     * @return
     */
    public List<T> queryList(Query query){
        return this.mongoTemplate.find(query, getEntityClass());
    }
    /**
     * 根据条件查询单个实体
     * @param query
     * @return
     */
    public T queryOne(Query query){
        return (T) this.mongoTemplate.findOne(query, getEntityClass());
    }
    /**
     * 根据分页条件查询
     * @param query
     * @param start 查询起始值
     * @param size 分页大小
     * @return
     */
    public List<T> getPage(Query query, int start, int size, Sort sort){
        query.skip(start);
        query.limit(size);
        if(null!=sort){
            query.with(sort);
        }
        return this.mongoTemplate.find(query, getEntityClass());
    }
    /**
     * 根据条件查询库中符合记录的总数,为分页查询服务
     * @param query
     * @return
     */
    public Long getPageCount(Query query){
        return this.mongoTemplate.count(query, getEntityClass());
    }
    /**
     * 根据id删除
     * @param id
     */
    public void deleteById(String id){
        Criteria criteria= Criteria.where("_id").in(id);
        if(null!=criteria){
            Query query=new Query();
            query.addCriteria(criteria);
           logger.info("[Mongo Dao] deleteById:" + query );
            if(null!=query && this.queryOne(query)!=null){
                this.mongoTemplate.remove(query, getEntityClass());
            }
        }
    }
    /**
     * 删除对象
     * @param t
     */
    public void delete(T t){
       logger.info("[Mongo Dao] t:" + t );
        this.mongoTemplate.remove(t);
    }
    /**
     * 更新满足条件的第一个记录
     * @param query
     * @param update
     */
    public void updateFirst(Query query, Update update){
        logger.info("[Mongo Dao ]updateFirst:query(" + query + "),update(" + update + ")");
        this.mongoTemplate.updateFirst(query, update, getEntityClass());
    }
    /**
     * 修改满足条件的多条记录
     * @param query
     * @param update
     */
    public void updateMulti(Query query, Update update){
        logger.info("[Mongo Dao ]updateMulti:query(" + query + "),update(" + update + ")");
        this.mongoTemplate.updateMulti(query, update, getEntityClass());
    }
    /**
     * 修改,如果要修改的对象不存在则添加
     * @param query
     * @param update
     */
    public void updateInser(Query query, Update update){
        logger.info("[Mongo Dao ]updateInser:query(" + query + "),update(" + update + ")");
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }
    /**
     * 钩子方法,由子类实现返回反射对象的类型
     * @return
     */
    protected  abstract Class getEntityClass();
}