package com.spring.serialize;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
/**
 * 序列化测试
 * @author leo
 *
 */
public class SerailizeTest {
	
	private final static Logger logger=LoggerFactory.getLogger(SerailizeTest.class);
	private static Random random=new Random();
	
	private static RuntimeSchema<School> schema=RuntimeSchema.createFrom(School.class);
	
	public static void main(String[] args) {
		
		//---------------------kryo start----------------------
//		try {
//		    long start=System.currentTimeMillis();
//			logger.info("kryo serialize start:{}",start);
//			kryoSerialize();
//		    long end=System.currentTimeMillis();
//			logger.info("kryo serialize time:{}",end-start);
//		} catch (IOException e) {
//			logger.error("",e);
//		}
//		try {
//		    long start=System.currentTimeMillis();
//			logger.info("kryo deserialize start:{}",start);
//			kryoDeserialize();
//		    long end=System.currentTimeMillis();
//			logger.info("kryo deserialize time:{}",end-start);
//		} catch (FileNotFoundException e) {
//			logger.error("",e);
//		}
		//-----------------------kryo end--------------------------------
		//-----------------------protostuff start-------------------------
//		try {
//			long start=System.currentTimeMillis();
//			logger.info("protostuff serialize start:{}",start);
//			protostuffSerialize();
//			long end=System.currentTimeMillis();
//			logger.info("protostuff serialize time:{}",end-start);
//		} catch (IOException e) {
//			logger.error("",e);
//		}
		
		try {
			long start=System.currentTimeMillis();
			logger.info("protostuff deserialize start:{}",start);
			protostuffDeserizliae();
			long end=System.currentTimeMillis();
			logger.info("protostuff deserialize time:{}",end-start);
		} catch (IOException e) {
			logger.error("",e);
		}
		
	}
	
	/**
	 * protostuff序列化
	 * @throws IOException 
	 */
	public static void protostuffSerialize() throws IOException{
		OutputStream out=null;
		try {
			File file=new File("D:/protostuff.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			byte[] inbytes=ProtostuffIOUtil.toByteArray(getSchool(), schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			out=new FileOutputStream(file);
			out.write(inbytes);
		} finally {
			if(null != out){
				out.close();
			}
		}
	}
	
	/**
	 * protostuff反序列化
	 * @throws IOException 
	 */
	public static void protostuffDeserizliae() throws IOException{
		
		InputStream input=null;
		try {
			School sch=new School();
			ProtostuffIOUtil.mergeFrom(toByteArray("D:/protostuff.txt"), sch, schema);
			logger.info("protostuff deserailize result:{}",JSONObject.toJSON(sch));
		} finally {
			if(null != input){
				input.close();
			}
		}
	}
	
	public static byte[] toByteArray(String filename) throws IOException {  
		  
        File f = new File(filename);  
        if (!f.exists()) {  
            throw new FileNotFoundException(filename);  
        }  
  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());  
        BufferedInputStream in = null;  
        try {  
            in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while (-1 != (len = in.read(buffer, 0, buf_size))) {  
                bos.write(buffer, 0, len);  
            }  
            return bos.toByteArray();  
        } catch (IOException e) {  
        	logger.error("",e);
            throw e;  
        } finally {  
            try {  
                in.close();  
            } catch (IOException e) {  
            	logger.error("",e);
            }  
            bos.close();  
        }  
    }  
	
	/**
	 * kryo序列化
	 * @throws IOException
	 */
	public static void kryoSerialize() throws IOException{
		Output output =null;
		try {
			Kryo kryo = new Kryo();
			File file=new File("D:/kryo.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			output=new Output(new FileOutputStream(file));
//			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
//			Output output = new Output(outputStream);
			School school=getSchool();
			kryo.writeObject(output, school);
		} finally {
			output.close();
		}
		
		
		
	}
	
	/**
	 * kryo反序列化
	 * @throws FileNotFoundException
	 */
	public static void kryoDeserialize() throws FileNotFoundException{
		Input input=null;
		try {
			Kryo kryo = new Kryo();
			File file=new File("D:/kryo.txt");
			input=new Input(new FileInputStream(file));
			School school=kryo.readObject(input, School.class);
			logger.info("deserialize result:{}",com.alibaba.fastjson.JSONObject.toJSON(school));
		} finally {
			input.close();
		}
		
		
	}
   
	
	public static School getSchool(){
		School school=new School();
		school.setName("西北师范大学");
		school.setFromDate(new Date());
		school.setFlo(1.22f);
		school.setYears(120l);
		school.setSet(getSet("setvalue"));
		school.setMap(getMap("key", "mapvalue"));
		school.setStudents(500);
		school.setStuList(getStuList(school.getStudents()));
		return school;
	}
	
	public static Set<String> getSet(String set){
		Set<String> res=new HashSet<>();
		res.add(set);
		return res;
	}
	
	public static Map<String,Object> getMap(String key,Object value){
		Map<String,Object> map=new HashMap<>();
		map.put(key, value);
		return map;
	}
	
	public static List<Student> getStuList(int size){
		List<Student> list=new ArrayList<>();
		for(int i=0;i<size;i++){
			list.add(getStudent(random.nextInt(18)));
		}
		return list;
	}
	
	public static Student getStudent(int age){
		Student stu=new Student();
		stu.setName("学生姓名");
		stu.setAge(age);
		return stu;
	}
}
