import { useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

function Trips() {
  const navigate = useNavigate();

  const { isLoading, error, data } = useQuery({
    queryKey: ["trips"],
    queryFn: async () => {
      const response = await fetch("http://localhost:8080/trips/list");
      if (!response.ok) throw new Error("Network response was not ok");
      const tripsData = await response.json();
      return tripsData;
    },
  });

  return (
    <div className="flex justify-center text-black">
      <div className="w-full lg:w-3/4 xl:w-1/2 mt-4 space-y-8">
        <h1 className="text-left mt-4 font-bold">Trips Page </h1>
        {isLoading && <p>Loading trips...</p>}
        {error && <p>Error: {error.message}</p>}
        {data && data.length > 0 && (
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
            </div>
            <div>
              {data.map((trip) => (
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
