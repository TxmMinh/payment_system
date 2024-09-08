import React, { useEffect, useState } from "react";
import { Button, Typography, message } from "antd";
import { CheckSquareOutlined, CloseSquareOutlined } from "@ant-design/icons";
import { useLocation } from "react-router-dom";
import LoadPage from "../LoadPage";
import axios from "axios";

const { Title } = Typography;

const PaymentExecute = () => {
  const location = useLocation();
  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(true);
  const [paymentExecuted, setPaymentExecuted] = useState(false); 

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const paymentId = queryParams.get("paymentId");
    const payerId = queryParams.get("PayerID");

    const executePayment = async () => {
      try {
        if (paymentId && payerId && !paymentExecuted) {
          setPaymentExecuted(true);
          
          const paymentExecute = { paymentId, payerId };
          
          const response = await axios.post(
            "http://localhost:8080/api/v1/payment/execute",
            paymentExecute
          );
          
          if (response.data.status === "success") {
            setStatus("success");
          } else {
            setStatus("fail");
            message.error("Payment execution failed");
          }
          
        } else if (!paymentId || !payerId) {
          setStatus("fail");
          message.error("Missing required payment details");
        }
      } catch (err) {
        setStatus("fail");
        message.error("Error during fetching data from the API");
      } finally {
        setLoading(false);
      }
    };

    executePayment();
  }, []); 

  if (loading) {
    return <LoadPage />;
  }

  return (
    <div className="payment-booking-page">
      <Title level={3}>Payment</Title>
      <div className="payment-container">
        <div className="col-sm-12">
          <div className="col-sm-10 col-sm-offset-1 text-center">
            {status === "success" ? (
              <div>
                <CheckSquareOutlined className="payment-success" />
                <div className="payment-container font-content">
                  <h3>Payment successful</h3>
                  <a href="/">
                    <Button type="primary" className="button-primary">Return to Home</Button>
                  </a>
                </div>
              </div>
            ) : (
              <div>
                <CloseSquareOutlined className="payment-cancel" />
                <div className="payment-container font-content">
                  <h3>Payment failed</h3>
                  <a href="/">
                    <Button type="primary" className="button-primary">Return to Home</Button>
                  </a>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default PaymentExecute;
