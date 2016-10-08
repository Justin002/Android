package com.beyondsoft.ep2p.model;

import java.io.Serializable;

/**
 * 债权信息实体
 * 
 * @author hardy.zhou
 *
 */
public class CreditorRightsInfoBean implements Serializable {

	private static final long serialVersionUID = 1453194216331436154L;
	private String transferId;
	private String receiptPlanId;
	private String borrowId;
	private String createUserId;
	private String receiptTime;
	private String transferCode;
	private String borrowCode;
	private String borrowName;
	private int amount;
	private int successAmount;
	private String transferStatus;
	private String receiptPlanStatus;
	private String receiptStatusName;

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getReceiptPlanId() {
		return receiptPlanId;
	}

	public void setReceiptPlanId(String receiptPlanId) {
		this.receiptPlanId = receiptPlanId;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public String getBorrowCode() {
		return borrowCode;
	}

	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(int successAmount) {
		this.successAmount = successAmount;
	}

	public String getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	public String getReceiptPlanStatus() {
		return receiptPlanStatus;
	}

	public void setReceiptPlanStatus(String receiptPlanStatus) {
		this.receiptPlanStatus = receiptPlanStatus;
	}

	public String getReceiptStatusName() {
		return receiptStatusName;
	}

	public void setReceiptStatusName(String receiptStatusName) {
		this.receiptStatusName = receiptStatusName;
	}

}
