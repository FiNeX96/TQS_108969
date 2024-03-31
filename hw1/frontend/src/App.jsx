
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Home from './pages/Home'
import TripDetails from './pages/TripDetails'
import Trips from './pages/Trips'
import Tickets from './pages/Tickets'
const queryClient = new QueryClient();
import './App.css'

function App() {

  return (
    <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/trips" element={<Trips />} />
        <Route path="/trip_details/:id" element={<TripDetails />} />
        <Route path="/tickets" element={<Tickets />} />
        <Route path="*" element={<Home />} />
      </Routes>
    </BrowserRouter>
    </QueryClientProvider>
  )

}

export default App
