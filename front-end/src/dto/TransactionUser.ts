export interface TransactionUser {
    id: number;
    transactionId: string;
    gross: number;
    net: number;
    fee: number; 
    description: string | null;
    transactionStatus: string;
    typeCurrency: string;
    createdDate: string;
}