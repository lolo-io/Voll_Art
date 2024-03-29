package iut.vollart.models;

import iut.vollart.util.BaseFileUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


public class Model implements Serializable{

	public float xrot = 90;
    public float yrot = 0;
    public float zrot = 0;
    public float xpos = 0;
    public float ypos = 0;
    public float zpos = 0;
    public float scale = 50f;
    public float alpha = 1f;
    public int STATE = STATE_DYNAMIC;
    public static final int STATE_DYNAMIC = 0;
    public static final int STATE_FINALIZED = 1;
    
	
	private Vector<Group> groups = new Vector<Group>();

	protected HashMap<String, Material> materials = new HashMap<String, Material>();
	
	public Model() {

		materials.put("default",new Material("default"));
	}
	
	public void addMaterial(Material mat) {

		materials.put(mat.getName(), mat);
	}
	
	public Material getMaterial(String name) {
		return materials.get(name);
	}
	
	public void addGroup(Group grp) {
		if(STATE == STATE_FINALIZED)
			grp.finalize();
		groups.add(grp);
	}
	
	public Vector<Group> getGroups() {
		return groups;
	}
	
	public void setFileUtil(BaseFileUtil fileUtil) {
		for (Iterator iterator = materials.values().iterator(); iterator.hasNext();) {
			Material mat = (Material) iterator.next();
			mat.setFileUtil(fileUtil);
		}
	}
	
	
	public HashMap<String, Material> getMaterials() {
		return materials;
	}

	public void setScale(float f) {
		this.scale += f;
		if(this.scale < 0.0001f)
			this.scale = 0.0001f;
	}

	public void setXrot(float dY) {
		this.xrot += dY;
	}

	public void setYrot(float dX) {
		this.yrot += dX;
	}

	public void setXpos(float f) {
		this.xpos += f;
	}

	public void setYpos(float f) {
		this.ypos += f;
	}
	
	public void setAlpha(float f) {
		if (alpha >= 0f && alpha <= 1f)
			this.alpha += f;
		if (alpha<0)
			alpha=0;
		if (alpha>1)
			alpha=1;
	}
	
	public void raz()
	{
		this.xrot = 90;
		this.yrot = 0;
		this.zrot = 0;
		this.xpos = 0;
		this.ypos = 0;
		this.zpos = 0;
		this.scale = 50f;
		this.alpha = 1f;
	}
	

	public void finalize() {
		if(STATE != STATE_FINALIZED) {
			STATE = STATE_FINALIZED;
			for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
				Group grp = (Group) iterator.next();
				grp.finalize();
				grp.setMaterial(materials.get(grp.getMaterialName()));
			}
			for (Iterator<Material> iterator = materials.values().iterator(); iterator.hasNext();) {
				Material mtl = iterator.next();
				mtl.finalize();
			}
		}
	}
	
}
