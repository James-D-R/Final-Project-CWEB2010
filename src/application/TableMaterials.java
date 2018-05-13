/**
 * 
 */
package application;

/**
 * @author remjamd
 *
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableMaterials {

	private final SimpleStringProperty material;
	
	public TableMaterials(String material) {
		
		this.material = new SimpleStringProperty(material);
	}
	
	//material
		public void setMaterial(String materialName) {
			material.set(materialName);
		}
		
		public String getMaterial() {
			return material.get();
		}

		public StringProperty materialProperty() {
			// TODO Auto-generated method stub
			return material;
		}
}
