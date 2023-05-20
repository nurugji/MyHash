package com.myhash.object;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class History {
	final static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");
	
	boolean correct;
	Date date;
	String memo;
	
	public History() {}
	
	public History(boolean correct) {
		this.correct = correct;
		this.date = new Date();
		this.memo = "";
	}
	
	public History(boolean correct, String memo) {
		this.correct = correct;
		this.memo = memo;
		this.date = new Date();
	}
	
	public History(boolean correct, Date date, String memo) {
		this.correct = correct;
		this.memo = memo;
		this.date = date;
	}
	
	public boolean getCorrect() {
		return correct;
	}
	public String getCorrecttoString() {
		return this.correct ? "correct" : "incorrect";
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDate() {
		return simpleDate.format(date);
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\'" + correct + "\' ");
		sb.append("\'" + simpleDate.format(date) + "\' ");
		sb.append("\'" + memo + "\' ");
		return sb.toString();
	}
}
