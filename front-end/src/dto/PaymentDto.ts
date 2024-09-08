export interface PaymentDto {
    method: string;
    amount: number;
    currency: string;
    description: string;
}