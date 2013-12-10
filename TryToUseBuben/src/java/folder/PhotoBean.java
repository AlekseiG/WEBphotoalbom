package folder;
import hr.Photo;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author Vitaly
 */
@ManagedBean
@ViewScoped
public class PhotoBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;      
    

    public List<Photo> getPhotos() {
        return em.createQuery("select * from APP.PHOTOS;", Photo.class).getResultList();
    }
    
   /* public List<String> getEmpDep() {
        return em.createNativeQuery("select e.* " 
                + "from EMPLOYEE e, DEPARTMENT d " 
                + "where e.DEPARTMENTID = d.ID", Photo.class).getResultList();
    }
    
    public boolean depIsEmpty(long id){
        return em.createNativeQuery("select e.* " 
                + "from EMPLOYEE e " 
                + "where e.DEPARTMENTID = " + id, Photo.class).getResultList().isEmpty();        
    }*/

    public void deletePhoto(Long id) {
        if (id == null) {
            return;
        }
        try {
            utx.begin();
            Photo e = em.find(Photo.class, id);
            if(e!= null) {
                em.remove(e);
            }
            
            utx.commit();
        } catch (Exception ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DB Error:", ex.getLocalizedMessage()));
            ex.printStackTrace(System.err);
            try {
                utx.rollback();
            } catch (Exception exc) {
                exc.printStackTrace(System.err);
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DB Error:", exc.getLocalizedMessage()));
            }
        }
    }
    
    public void createPhoto(PhotoEditBean newPhoto){
      
                
        try {            
            utx.begin();
            Photo ph = new Photo();            
            //emp.setId(();    

            ph.setOwner(newPhoto.getOwner());
            ph.setName(newPhoto.getName());
           // emp.setId(1);
            

            if(ph!= null) {
                em.persist(ph);
            }
            
            utx.commit();
        } catch (Exception ex) {
            //FacesContext ctx = FacesContext.getCurrentInstance();
           // ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, newEmp.getEmail() , ex.getLocalizedMessage()));
         /*   ex.printStackTrace(System.err);
            try {
                utx.rollback();
            } catch (Exception exc) {              
                exc.printStackTrace(System.err);
            }*/
        }
    }
       
   
    
}
