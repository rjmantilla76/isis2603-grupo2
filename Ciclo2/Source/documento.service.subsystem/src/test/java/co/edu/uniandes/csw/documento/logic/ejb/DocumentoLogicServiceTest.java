
package co.edu.uniandes.csw.documento.logic.ejb;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;


import co.edu.uniandes.csw.documento.logic.dto.DocumentoDTO;
import co.edu.uniandes.csw.documento.logic.api.IDocumentoLogicService;
import co.edu.uniandes.csw.documento.persistence.DocumentoPersistence;
import co.edu.uniandes.csw.documento.persistence.api.IDocumentoPersistence;
import co.edu.uniandes.csw.documento.persistence.entity.DocumentoEntity;

@RunWith(Arquillian.class)
public class DocumentoLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(DocumentoLogicService.class.getPackage())
				.addPackage(DocumentoPersistence.class.getPackage())
				.addPackage(DocumentoEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IDocumentoLogicService documentoLogicService;
	
	@Inject
	private IDocumentoPersistence documentoPersistence;	

	@Before
	public void configTest() {
		try {
			clearData();
			insertData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		List<DocumentoDTO> dtos=documentoPersistence.getDocumentos();
		for(DocumentoDTO dto:dtos){
			documentoPersistence.deleteDocumento(dto.getId());
		}
	}

	private List<DocumentoDTO> data=new ArrayList<DocumentoDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			DocumentoDTO pdto=new DocumentoDTO();
			pdto.setType(generateRandom(String.class));
			pdto.setFecha(generateRandom(Date.class));
			pdto.setAutor(generateRandom(String.class));
			pdto.setDescripcion(generateRandom(String.class));
			pdto.setName(generateRandom(String.class));
			pdto=documentoPersistence.createDocumento(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createDocumentoTest(){
		DocumentoDTO ldto=new DocumentoDTO();
		ldto.setType(generateRandom(String.class));
		ldto.setFecha(generateRandom(Date.class));
		ldto.setAutor(generateRandom(String.class));
		ldto.setDescripcion(generateRandom(String.class));
		ldto.setName(generateRandom(String.class));
		
		
		DocumentoDTO result=documentoLogicService.createDocumento(ldto);
		
		Assert.assertNotNull(result);
		
		DocumentoDTO pdto=documentoPersistence.getDocumento(result.getId());
		
		Assert.assertEquals(ldto.getType(), pdto.getType());	
		Assert.assertEquals(ldto.getFecha(), pdto.getFecha());	
		Assert.assertEquals(ldto.getAutor(), pdto.getAutor());	
		Assert.assertEquals(ldto.getDescripcion(), pdto.getDescripcion());	
		Assert.assertEquals(ldto.getName(), pdto.getName());	
	}
	
	@Test
	public void getDocumentosTest(){
		List<DocumentoDTO> list=documentoLogicService.getDocumentos();
		Assert.assertEquals(list.size(), data.size());
        for(DocumentoDTO ldto:list){
        	boolean found=false;
            for(DocumentoDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getDocumentoTest(){
		DocumentoDTO pdto=data.get(0);
		DocumentoDTO ldto=documentoLogicService.getDocumento(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getType(), ldto.getType());
		Assert.assertEquals(pdto.getFecha(), ldto.getFecha());
		Assert.assertEquals(pdto.getAutor(), ldto.getAutor());
		Assert.assertEquals(pdto.getDescripcion(), ldto.getDescripcion());
		Assert.assertEquals(pdto.getName(), ldto.getName());
        
	}
	
	@Test
	public void deleteDocumentoTest(){
		DocumentoDTO pdto=data.get(0);
		documentoLogicService.deleteDocumento(pdto.getId());
        DocumentoDTO deleted=documentoPersistence.getDocumento(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateDocumentoTest(){
		DocumentoDTO pdto=data.get(0);
		
		DocumentoDTO ldto=new DocumentoDTO();
		ldto.setId(pdto.getId());
		ldto.setType(generateRandom(String.class));
		ldto.setFecha(generateRandom(Date.class));
		ldto.setAutor(generateRandom(String.class));
		ldto.setDescripcion(generateRandom(String.class));
		ldto.setName(generateRandom(String.class));
		
		
		documentoLogicService.updateDocumento(ldto);
		
		
		DocumentoDTO resp=documentoPersistence.getDocumento(pdto.getId());
		
		Assert.assertEquals(ldto.getType(), resp.getType());	
		Assert.assertEquals(ldto.getFecha(), resp.getFecha());	
		Assert.assertEquals(ldto.getAutor(), resp.getAutor());	
		Assert.assertEquals(ldto.getDescripcion(), resp.getDescripcion());	
		Assert.assertEquals(ldto.getName(), resp.getName());	
	}
	
	public <T> T generateRandom(Class<T> objectClass){
		Random r=new Random();
		if(objectClass.isInstance(String.class)){
			String s="";
			for(int i=0;i<10;i++){
				char c=(char)(r.nextInt()/('Z'-'A')+'A');
				s=s+c;
			}
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Integer.class)){
			Integer s=r.nextInt();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Long.class)){
			Long s=r.nextLong();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(java.util.Date.class)){
			java.util.Calendar c=java.util.Calendar.getInstance();
			c.set(java.util.Calendar.MONTH, r.nextInt()/12);
			c.set(java.util.Calendar.DAY_OF_MONTH,r.nextInt()/30);
			c.setLenient(false);
			return objectClass.cast(c.getTime());
		} 
		return null;
	}
	
}