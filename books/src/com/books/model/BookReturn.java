package com.books.model;

public class BookReturn {
	private String borrowid;
	private String bookid;
	private String ISBN;
	private String bookname;
	private String borrowdate;
	private String shouldreturn;
	private String returndate;
	private int hasreturn;
	private int fine;
	
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getBorrowdate() {
		return borrowdate;
	}
	public void setBorrowdate(String borrowdate) {
		this.borrowdate = borrowdate;
	}
	public String getShouldreturn() {
		return shouldreturn;
	}
	public void setShouldreturn(String shouldreturn) {
		this.shouldreturn = shouldreturn;
	}
	public String getReturndate() {
		return returndate;
	}
	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}
	public int getHasreturn() {
		return hasreturn;
	}
	public void setHasreturn(int hasreturn) {
		this.hasreturn = hasreturn;
	}
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public String getBorrowid() {
		return borrowid;
	}
	public void setBorrowid(String borrowid) {
		this.borrowid = borrowid;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
}
