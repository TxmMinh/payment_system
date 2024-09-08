import { Button } from 'antd';
import React from 'react';

const PaymentCancel = () => {
    return (
        <div className="container-cancel">
            <h1 className="text-center mt-5">Payment Cancelled</h1>
            <a href="/" className="">
                <Button type="primary" className="button-primary">Return to Home</Button>
            </a>
        </div>
    );
};

export default PaymentCancel;
