import React from 'react';
import { Button, Card } from 'antd';
import { useNavigate } from 'react-router-dom'; // Updated hook for React Router v6

const DashboardPage = () => {
    const navigate = useNavigate(); // Use useNavigate instead of useHistory

    const handlePaymentClick = () => {
        navigate('/payment'); // Navigate to the payment page
    };

    const handleWalletClick = () => {
        navigate('/wallet'); // Navigate to the wallet page
    };

    return (
        <div className="dashboard-container">
            <Card className="dashboard-card" style={{ textAlign: 'center', padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
                <h1 className="text-center">Dashboard</h1>
                <Button type="primary" onClick={handlePaymentClick} style={{ marginBottom: '10px' }} block>
                    Payment
                </Button>
                <Button type="primary" onClick={handleWalletClick} block>
                    Wallet
                </Button>
            </Card>
        </div>
    );
};

export default DashboardPage;
