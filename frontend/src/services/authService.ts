
import type { LoginRequest, LoginResponse, RegisterRequest, User } from '../types/auth';
import { apiRequest } from './api';

export function login(data:LoginRequest){
    return apiRequest<LoginResponse>("/api/auth/login",{
        method:"POST",
        body:JSON.stringify(data),
    });
}


export function register(data:RegisterRequest){
    return apiRequest<User>("/api/auth/register",{
        method:"POST",
        body:JSON.stringify(data)
    })
}