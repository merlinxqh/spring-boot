package com.spring.serialize;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
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
	
	//PROTOSTUFF
	private static RuntimeSchema<School> schema=RuntimeSchema.createFrom(School.class);
	
	//KRYO
	private static Kryo kryo=new Kryo();
	
	//FST
	private static FSTConfiguration fst = FSTConfiguration.createStructConfiguration();
	
	static{
		fst.registerClass(School.class,Student.class);
	}
	
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
		
//		try {
//			long start=System.currentTimeMillis();
//			logger.info("protostuff deserialize start:{}",start);
//			protostuffDeserizliae();
//			long end=System.currentTimeMillis();
//			logger.info("protostuff deserialize time:{}",end-start);
//		} catch (IOException e) {
//			logger.error("",e);
//		}
		
		//--------------------protostuff end----------------------------
		
		//------------------- fst start ----------------------------
		try {
			long start=System.currentTimeMillis();
			logger.info("fst serialize start:{}",start);
			fstSerialize();
			long end=System.currentTimeMillis();
			logger.info("fst serialize time:{}",end-start);
		} catch (IOException e) {
			logger.error("",e);
		}
		
//		try {
//			long start=System.currentTimeMillis();
//			logger.info("fst deserialize start:{}",start);
//			fstDeserialize();
//			long end=System.currentTimeMillis();
//			logger.info("fst deserialize time:{}",end-start);
//		} catch (IOException e) {
//			logger.error("",e);
//		}
		//-------------------fst end----------------------------
		
	}
	/**
	 * TODO fst 序列化 对象内包含其他对象 这样有问题 
	 * @throws IOException
	 */
	public static void fstSerialize() throws IOException{
		OutputStream out=null;
		try {
			File file=new File("D:/fst.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			out=new FileOutputStream(file);
			School sch=getSchool();
			mywriteMethod(out, sch);
		} finally {
			if(null != out){
				out.close();
			}
		}
		
	}
	
	public static void fstDeserialize() throws IOException{
		byte[] bytes=toByteArray("D:/fst.txt");
		Student sch=(Student) fst.asObject(bytes);
		logger.info("fst deserailize result:{}",JSONObject.toJSON(sch));
	}
	
	public static School myreadMethod(InputStream stream) throws Exception
	{
	    FSTObjectInput in = fst.getObjectInput(stream);
	    School result = (School) in.readObject(School.class);
	    // DON'T: in.close(); here prevents reuse and will result in an exception      
	    stream.close();
	    return result;
	}

	public static void mywriteMethod( OutputStream stream, School toWrite ) throws IOException 
	{
	    FSTObjectOutput out = fst.getObjectOutput(stream);
	    out.writeObject( toWrite, School.class );
	    // DON'T out.close() when using factory method;
	    out.flush();
	    stream.close();
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
		School sch=new School();
		ProtostuffIOUtil.mergeFrom(toByteArray("D:/protostuff.txt"), sch, schema);
		logger.info("protostuff deserailize result:{}",JSONObject.toJSON(sch));
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
			File file=new File("D:/kryo.txt");
			input=new Input(new FileInputStream(file));
			School school=kryo.readObject(input, School.class);
			logger.info("deserialize result:{}",com.alibaba.fastjson.JSONObject.toJSON(school));
		} finally {
			input.close();
		}
	}
	
	// jdk原生序列换方案
	public static byte[] jdkserialize(Object obj) {
		try (
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
		){
			oos.writeObject(obj);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object jdkdeserialize(byte[] bits) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bits);
				ObjectInputStream ois = new ObjectInputStream(bais);

		) {
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
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
