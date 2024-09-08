/* eslint-disable */
import { Spin, Typography } from 'antd';

const { Title } = Typography;

const LoadPage = () => {
  return (
    <div className="payment-booking-page">
      <Title level={3}>Payment</Title>
      <div className="payment-container">
        <div className="col-sm-12 ">
          <div className="col-sm-10 col-sm-offset-1 text-center">
            <Spin size="large" />
            <p>Loading</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoadPage;
