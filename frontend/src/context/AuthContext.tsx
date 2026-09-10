import { createContext, useContext, useState, type ReactNode } from "react";
import type { LoginRequest, User } from "../types/auth";
import { login as loginRequest } from "../services/authService";

interface AuthContextValue {
  user: User | null;
  isAuthenticate: boolean;
  isLoading: boolean;
  login: (data: LoginRequest) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const login = async (data: LoginRequest): Promise<void> => {
    setIsLoading(true);
    try {
      const response = await loginRequest(data);
      setUser(response.user);
    } finally {
      setIsLoading(false);
    }
  };

  const logout = (): void => {
    setUser(null);
  };

  const value: AuthContextValue = {
    user,
    isAuthenticate: user != null,
    isLoading,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthContextValue {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }
  return context;
}
