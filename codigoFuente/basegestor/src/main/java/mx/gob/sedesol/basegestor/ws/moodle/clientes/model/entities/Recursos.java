package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

public class Recursos {
	private List<Datos> books;
	private List<Datos> files;
	private List<Datos> folders;
	private List<Datos> ims;
	private List<Datos> labels;
	private List<Datos> pages;
	private List<Datos> urls;

	@Override
	public String toString() {
		return "Recusros [books=" + books + ", files=" + files + ", folders=" + folders + ", ims=" + ims + ", labels="
				+ labels + ", pages=" + pages + ", urls=" + urls + "]";
	}

	public List<Datos> getBooks() {
		return books;
	}

	public void setBooks(List<Datos> books) {
		this.books = books;
	}

	public List<Datos> getFiles() {
		return files;
	}

	public void setFiles(List<Datos> files) {
		this.files = files;
	}

	public List<Datos> getFolders() {
		return folders;
	}

	public void setFolders(List<Datos> folders) {
		this.folders = folders;
	}

	public List<Datos> getIms() {
		return ims;
	}

	public void setIms(List<Datos> ims) {
		this.ims = ims;
	}

	public List<Datos> getLabels() {
		return labels;
	}

	public void setLabels(List<Datos> labels) {
		this.labels = labels;
	}

	public List<Datos> getPages() {
		return pages;
	}

	public void setPages(List<Datos> pages) {
		this.pages = pages;
	}

	public List<Datos> getUrls() {
		return urls;
	}

	public void setUrls(List<Datos> urls) {
		this.urls = urls;
	}
}
