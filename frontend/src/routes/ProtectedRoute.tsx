import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export default function ProtectedRoute({ children }: ProtectedRouteProps) {
  const { isAuthenticate } = useAuth();

  if (!isAuthenticate) {
    return <Navigate to="/login" replace />;
  }
  return children;
}
