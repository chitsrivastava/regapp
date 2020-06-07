import { Image } from './image.model';

export interface User {
    registrationId?: number;
    userName: string;
    contactNumber: string;
    email: string;
    image: any;
    registrationType: string;
    numberOfTickets: number;
    approved?: boolean;
}