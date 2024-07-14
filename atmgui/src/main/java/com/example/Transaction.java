package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
	private int transaction_id;
	private int user_id;
	private String transaction_type;
	private int amount;
}
