import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./css/index"
import PaymentForm from "./payment/PaymentForm";
import PaymentExecute from "./payment/PaymentExecute";
import PaymentCancel from "./payment/PaymentCancel";
import TransactionTable from "./transaction/TransactionTable";
import DashboardPage from "./home/DashboardPage";

function App() {
  return (
    <Router>
      <Routes>        
          <Route path="/" element={<DashboardPage/>} />
          <Route path="/wallet" element={<TransactionTable />} />
          <Route path="/payment" element={<PaymentForm />} />
          <Route path="/payment/execute" element={<PaymentExecute />} />
          <Route path="/payment/cancel" element={<PaymentCancel />} />
      </Routes>
    </Router>
  );
}

export default App;
