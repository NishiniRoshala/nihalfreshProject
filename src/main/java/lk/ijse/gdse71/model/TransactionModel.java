package lk.ijse.gdse71.model;


import lk.ijse.gdse71.dto.TransactionDTO;
import lk.ijse.gdse71.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionModel {

    public String getNextUserId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select user_id from transaction order by user_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last User ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("U%03d", newIdIndex); // Return the new User ID in format Unnn
        }
        return "U001"; // Return the default User ID if no data is found
    }

    public boolean saveTransaction(TransactionDTO transactionDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into transaction values (?,?,?,?)",
                transactionDTO.getUserId(),
                transactionDTO.getUserName(),
                transactionDTO.getPasswordHash(),
                transactionDTO.getRole()
        );
    }

    public ArrayList<TransactionDTO> getAllTransactions() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from transaction");

        ArrayList<TransactionDTO> transactionDTOS = new ArrayList<>();

        while (rst.next()) {
            TransactionDTO transactionDTO = new TransactionDTO(
                    rst.getString(1),  // UserID
                    rst.getString(2),  // UserName
                    rst.getString(3),  // PasswordHash
                    rst.getString(4)   // Role
            );
            transactionDTOS.add(transactionDTO);
        }
        return transactionDTOS;
    }

    public boolean updateTransaction(TransactionDTO transactionDTO) throws SQLException {
        return CrudUtil.execute(
                "update transaction set user_name=?, password_hash=?, role=? where user_id=?",
                transactionDTO.getUserName(),
                transactionDTO.getPasswordHash(),
                transactionDTO.getRole(),
                transactionDTO.getUserId()
        );
    }

    public boolean deleteTransaction(String userId) throws SQLException {
        return CrudUtil.execute("delete from transaction where user_id=?", userId);
    }

    public ArrayList<String> getAllUserIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select user_id from transaction");

        ArrayList<String> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getString(1));
        }

        return userIds;
    }

    public TransactionDTO findById(String userId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from transaction where user_id=?", userId);

        if (rst.next()) {
            return new TransactionDTO(
                    rst.getString(1),  // UserID
                    rst.getString(2),  // UserName
                    rst.getString(3),  // PasswordHash
                    rst.getString(4)   // Role
            );
        }
        return null;
    }
}
