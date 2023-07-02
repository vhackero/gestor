package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

public class Categoria {
	private int id;
	private String name; // new category name
	private int parent;// Valor por defecto para "0" //the parent category id
	private int newparent;
    private int recursive;
						// inside which the new category will be created
						// - set to 0 for a root category
	private String idnumber;// Opcional the new category idnumber
	private String description;// Opcional the new category description
	private int descriptionformat;// Valor por defecto para "1" description
									// format (1 = HTML, 0 = MOODLE, 2 = PLAIN
									// or 4 = MARKDOWN)
	private String theme;// Opcional //the new category theme. This option must
							// be enabled on moodle
	private int sortorder;
	private int coursecount;
	private int visible;
	private int visibleold;
	private int timemodified;
	private int depth;
	private String path;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
	
	/**
     * @return the newparent
     */
    public int getNewparent() {
        return newparent;
    }
    /**
     * @param newparent the newparent to set
     */
    public void setNewparent(int newparent) {
        this.newparent = newparent;
    }

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	
	  /**
     * @return the recursive
     */
    public int getRecursive() {
        return recursive;
    }
    /**
     * @param recursive the recursive to set
     */
    public void setRecursive(int recursive) {
        this.recursive = recursive;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDescriptionformat() {
		return descriptionformat;
	}

	public void setDescriptionformat(int descriptionformat) {
		this.descriptionformat = descriptionformat;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getSortorder() {
		return sortorder;
	}

	public void setSortorder(int sortorder) {
		this.sortorder = sortorder;
	}

	public int getCoursecount() {
		return coursecount;
	}

	public void setCoursecount(int coursecount) {
		this.coursecount = coursecount;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public int getVisibleold() {
		return visibleold;
	}

	public void setVisibleold(int visibleold) {
		this.visibleold = visibleold;
	}

	public int getTimemodified() {
		return timemodified;
	}

	public void setTimemodified(int timemodified) {
		this.timemodified = timemodified;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", name=" + name + ", parent=" + parent + ", idnumber=" + idnumber
				+ ", description=" + description + ", descriptionformat=" + descriptionformat + ", theme=" + theme
				+ ", sortorder=" + sortorder + ", coursecount=" + coursecount + ", visible=" + visible + ", visibleold="
				+ visibleold + ", timemodified=" + timemodified + ", depth=" + depth + ", path=" + path + "\n\n]";
	}

}
