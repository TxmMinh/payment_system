import React, { useState } from 'react';
import { Input, Select, Button, Card, Form } from 'antd';
import axios from 'axios';

const { Option } = Select;

interface PaymentDto {
    amount: number; 
    currency: string; 
    description: string;
}

const PaymentForm = () => {
    const [method] = useState('paypal'); // Hardcoded to 'paypal'

    const handleSubmit = async (values: PaymentDto) => {
        const { amount, currency, description } = values;
        try {
            const response = await axios.post('http://localhost:8080/api/v1/payment/create', {
                method,
                amount,
                currency,
                description
            });
            const redirectUrl = response.data.redirectUrl;
            window.location.href = redirectUrl;
        } catch (error) {
            console.error('Payment creation failed:', error);
        }
    };

    return (
        <div className="container">
            <h1 className="text-center mt-5">Paypal Payment Integration</h1>
            <Card className="mt-5 p-3" style={{ maxWidth: '500px', margin: '0 auto' }}>
                <Form layout="vertical" onFinish={handleSubmit}>
                    <Form.Item label="Payment Method">
                        <Input value={method} readOnly />
                    </Form.Item>
                    <Form.Item
                        label="Amount"
                        name="amount"
                        rules={[{ required: true, message: 'Please input the amount!' }]}
                    >
                        <Input type="number" />
                    </Form.Item>
                    <Form.Item
                        label="Currency"
                        name="currency"
                        initialValue="USD"
                    >
                        <Select>
                            <Option value="USD">USD</Option>
                            <Option value="EUR">EUR</Option>
                            <Option value="GBP">GBP</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item label="Description" name="description">
                        <Input />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" block>
                            Pay with Paypal
                        </Button>
                        <a href="/">
                            <Button type="primary" className="button-primary">Return to Home</Button>
                        </a>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default PaymentForm;
