import React, { useEffect, useState } from "react";
import axios from "axios";
import { TransactionUser } from "../dto/TransactionUser";
import { WalletUser } from "../dto/WalletUser";
import { Table, Button, Card, Pagination, Spin } from "antd";
import { ColumnsType } from "antd/es/table";

const TransactionTable: React.FC = () => {
  const [transactions, setTransactions] = useState<TransactionUser[]>([]);
  const [wallet, setWallet] = useState<WalletUser | null>(null);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);

  const pageSize = 10; // Default to 10 records per page

  useEffect(() => {
    fetchTransactions(currentPage);
    fetchWallet();
  }, [currentPage]);

  const fetchTransactions = async (page: number) => {
    setLoading(true);
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/transaction/user?page=${page}&size=${pageSize}`
      );
      const data = response.data;
      setTransactions(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Error fetching transactions:", error);
    }
    setLoading(false);
  };

  const fetchWallet = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/wallet/user`
      );
      setWallet(response.data);
    } catch (error) {
      console.error("Error fetching wallet information:", error);
    }
  };

  const handlePageChange = (newPage: number) => {
    setCurrentPage(newPage - 1); // AntD Pagination is 1-indexed, backend is 0-indexed
  };

  // Define table columns
  const columns: ColumnsType<TransactionUser> = [
    { title: "ID", dataIndex: "id", key: "id" },
    { title: "Description", dataIndex: "description", key: "description", render: (text) => text || "N/A" },
    { title: "Gross", dataIndex: "gross", key: "gross" },
    { title: "Net", dataIndex: "net", key: "net" },
    { title: "Fee", dataIndex: "fee", key: "fee" },
    { title: "Status", dataIndex: "transactionStatus", key: "transactionStatus" },
    { title: "Currency", dataIndex: "typeCurrency", key: "typeCurrency" },
    { title: "Transaction ID", dataIndex: "transactionId", key: "transactionId" },
    { title: "Created Date", dataIndex: "createdDate", key: "createdDate", render: (date) => new Date(date).toLocaleString() },
  ];

  return (
    <div style={{ margin: '100px' }}>
      {loading ? (
        <Spin tip="Loading..." />
      ) : (
        <>
          {/* Display Wallet Information */}
          {wallet && (
            <Card title="Wallet Information" bordered={false} style={{ marginBottom: "20px" }}>
              <p>
                Balance: <strong>{wallet.balance} {wallet.typeCurrency}</strong>
              </p>
            </Card>
          )}

          <a href="/payment">
            <Button type="primary" style={{ marginBottom: "20px" }}>
              Payment
            </Button>
          </a>

          <h2>Transaction List</h2>
          {/* Display Transaction Table */}
          <Table
            columns={columns}
            dataSource={transactions}
            pagination={false}
            rowKey="id"
            bordered
          />

          {/* Pagination controls */}
          <Pagination
            current={currentPage + 1}
            total={totalPages * pageSize}
            pageSize={pageSize}
            onChange={handlePageChange}
            style={{ marginTop: "20px", textAlign: "right" }}
          />
        </>
      )}
    </div>
  );
};

export default TransactionTable;
