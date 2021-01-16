package thunb.listener;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import thunb.utilities.Utilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Banh Bao
 */
public class SerletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
       //
        try {
            String allowExtensionMapPath = context.getRealPath("/WEB-INF/allowing_extension.properties");
            List<String> allowExtension = Utilities.loadAllowingExtensionList(allowExtensionMapPath);
            if(allowExtension!=null){
                context.setAttribute("ALLOW_EXTENSIONS", allowExtension);
            }
            //
            String uploadDir = context.getRealPath("/resources/imgs");
           
            if (uploadDir == null || uploadDir.trim().isEmpty()) {
                throw new Exception("IMAGE UPLOAD DIRECTORY CANNOT BE EMPTY");
            }
            Utilities.setIMAGE_SAVING_FOLDER(uploadDir);
            
        } catch (Exception e) {
            Logger.getLogger(SerletContextListener.class.getName()).log(Level.SEVERE,"SerletContextListener:" + e.getMessage(), e);
        }
            
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
