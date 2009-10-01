package init;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.single.desktopoa.common.deptment.Deptment;
import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.common.user.User;

public class InitData {

	private static SessionFactory factory;
	
	private static SessionFactory getSessionFactory(){
		if(factory==null)
			factory=new Configuration().configure().buildSessionFactory();
		return factory;
	}
	
	public static void main(String[] args) {
		
		
		Configuration cfg=new Configuration().configure();
		SchemaExport export=new SchemaExport(cfg);
		export.create(true, true);
		
//		export.drop(true, true);
		initUserData();
	}

	private static void initUserData() {
		Session session=getSessionFactory().openSession();
		session.beginTransaction();
		
		//部门创建
		Deptment deptment=new Deptment();
		deptment.setName("总经理");
		session.save(deptment);
		
		Person person=new Person();
		person.setName("管理员");
		person.setDeptment(deptment);
		
		User user=new User();
		user.setUsername("admin");
		user.setPassword("123");
		
		person.setAccount(user);
		
		session.save(user);
		session.save(person);
		
		person=new Person();
		person.setName("张三");
		person.setDeptment(deptment);
		
		user=new User();
		user.setUsername("zhangsan");
		user.setPassword("123");
		
		person.setAccount(user);
		
		session.save(user);
		session.save(person);
		
		session.getTransaction().commit();
		
		session.close();
		
		
		
	}
	
	
	
}
