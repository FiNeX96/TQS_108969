import { useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

function Trips() {
  const navigate = useNavigate();
  const [selectedOrigin, setSelectedOrigin] = useState("");
  const [selectedDestination, setSelectedDestination] = useState("");
  const [selectedDate, setSelectedDate] = useState("");
  const [selectedCurrency, setSelectedCurrency] = useState("EUR");

  const fetchData = async (url) => {
    const response = await fetch(url);
    if (!response.ok) throw new Error("Network response was not ok");
    return await response.json();
  };

  const tripsQuery = useQuery({
    queryKey: ["trips", selectedOrigin, selectedDestination, selectedDate],
    queryFn: async () => {
      // Construct the query string dynamically
      let queryString = "";
      if (selectedOrigin) {
        queryString += `origin=${selectedOrigin}&`;
      }
      if (selectedDestination) {
        queryString += `destination=${selectedDestination}&`;
      }
      if (selectedDate) {
        queryString += `date=${selectedDate}&`;
      }
      // Remove the trailing '&' if it exists
      if (queryString.endsWith("&")) {
        queryString = queryString.slice(0, -1);
      }

      // Use the constructed query string in the fetch call
      return await fetchData(`http://localhost:8080/trips/list?${queryString}`);
    },
  });

  const originsQuery = useQuery({
    queryKey: ["origins"],
    queryFn: async () =>
      await fetchData("http://localhost:8080/trips/get_origins"),
  });

  const destinationsQuery = useQuery({
    queryKey: ["destinations"], // Corrected typo for consistency
    queryFn: async () =>
      await fetchData("http://localhost:8080/trips/get_destinations"),
  });

  const datesQuery = useQuery({
    queryKey: ["dates"],
    queryFn: async () =>
      await fetchData("http://localhost:8080/trips/get_dates"),
  });

  // Access data, loading states, and errors using destructuring
  const {
    isLoading: tripsLoading,
    error: tripsError,
    data: tripsData,
  } = tripsQuery;
  const {
    isLoading: originsLoading,
    error: originsError,
    data: originsData,
  } = originsQuery;
  const {
    isLoading: destinationsLoading,
    error: destinationsError,
    data: destinationsData,
  } = destinationsQuery;
  const {
    isLoading: datesLoading,
    error: datesError,
    data: datesData,
  } = datesQuery;

  return (
    <div className="flex justify-center text-black space-y-8">
      <div className="w-full lg:w-3/4 xl:w-1/2 mt-4 space-y-8">
        <h1 className="text-left mt-4 font-bold">Trips Page </h1>
        {(tripsLoading ||
          originsLoading ||
          destinationsLoading ||
          datesLoading) && <p>Loading trips...</p>}
        {(tripsError || originsError || destinationsError || datesError) && (
          <p>Error: {error.message}</p>
        )}

        <div className="flex flex-row space-x-4 ">
          <span>Select Origin</span>
          <select
            className="select bg-slate-200 select-success w-full max-w-xs"
            value={selectedOrigin}
            onChange={(e) => setSelectedOrigin(e.target.value)}
          >
            {originsData &&
              originsData.length > 0 &&
              originsData.map((origin) => (
                <option key={origin} value={origin}>
                  {origin}
                </option>
              ))}
          </select>

          <span>Select Destination</span>
          <select
            className="select bg-slate-200 select-success w-full max-w-xs"
            value={selectedDestination}
            onChange={(e) => setSelectedDestination(e.target.value)}
          >
            {destinationsData &&
              destinationsData.length > 0 &&
              destinationsData.map((destination) => (
                <option key={destination} value={destination}>
                  {destination}
                </option>
              ))}
          </select>

          <span>Select Date</span>
          <select
            className="select bg-slate-200 select-success w-max max-w-xs"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
          >
            {datesData &&
              datesData.length > 0 &&
              datesData.map((date) => (
                <option key={date} value={date}>
                  {date}
                </option>
              ))}
          </select>
        </div>

        {tripsData && tripsData.length > 0 && (
          <div className="border border-gray-300">
            <div className="bg-gray-100 flex">
              <div className="w-1/4 px-4 py-2 border-r border-gray-300">ID</div>
              <div className="w-1/4 px-4 py-2 border-r border-gray-300 ">
                Origin
              </div>
              <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                Destination
              </div>
              <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                Date
              </div>
              <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                Time
              </div>
              <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                Bus Number
              </div>
              <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                Base Ticket Price
              </div>
            </div>
            <div>
              { tripsData.length > 0 && tripsData.map((trip) => (
                <div
                  key={trip.id}
                  className="flex items-center bg-white hover:bg-gray-100 cursor-pointer"
                  onClick={() => navigate(`/trip_details/${trip.id}`)}
                >
                  <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                    {trip.id}
                  </div>
                  <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                    {trip.origin}
                  </div>
                  <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                    {trip.destination}
                  </div>
                  <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                    {trip.date}
                  </div>
                  <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                    {trip.time}
                  </div>
                  <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                    {trip.busID}
                  </div>
                  <div className="w-1/4 px-4 py-2 border-r border-gray-300">
                    {trip.price} {selectedCurrency}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Trips;
