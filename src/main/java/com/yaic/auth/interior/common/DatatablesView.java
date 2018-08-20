package com.yaic.auth.interior.common;

import java.util.List;
/**
 * 
 * @author 宋淑华
 *
 */
public class DatatablesView<T> {
	private List<T> data; // data 与datatales 加载的“dataSrc"对应

	private int recordsTotal;

	private int recordsFiltered;

	private int sEcho;

	public DatatablesView() {

	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

}
