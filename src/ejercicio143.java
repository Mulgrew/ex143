
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class ejercicio143 {

    static private Map <Integer, Integer> mapa = new HashMap <Integer, Integer>();
    
    public static void main(String[] args) {
        lerDocumento();
        crearXML();
    }
    
    public static void lerDocumento() {
        
        SAXBuilder builder = new SAXBuilder();
        
        try {
            try {
                Document doc = builder.build("pedidos.xml");
                Element raiz = doc.getRootElement();
                List pedidos = raiz.getChildren("pedido");
                
                Iterator <Element> i = pedidos.iterator();
                
                while (i.hasNext()) {
                    
                    Element e = i.next();
                    List productos = e.getChildren("producto");
                    
                    Iterator <Element> j = productos.iterator();
                    
                    while (j.hasNext()) {
                    
                        Element p = j.next();
                        
                        Productos pr = new Productos();
                        pr.setId(Integer.parseInt(p.getAttributeValue("id")));
                        pr.setCantidad(Integer.parseInt(p.getText()));
                        
                        mapa.put(pr.getId(), pr.getCantidad());
                    }
                } 
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
    }
    
    private static void crearXML() {
        
        Document d = new Document();
        Element e = new Element("productos");
        d.setRootElement(e);
        
        for (Integer key : mapa.keySet()) {
            
            Element nuevoP = new Element("producto");
            nuevoP.setText(String.valueOf(mapa.get(key)));
            nuevoP.setAttribute("id", String.valueOf(key));
            e.addContent(nuevoP); 
        }
            
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        
        try {
            xmlOutput.output(d, new FileWriter("producto.xml"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }     
    }
}

class Productos {
    private int id;
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   
}