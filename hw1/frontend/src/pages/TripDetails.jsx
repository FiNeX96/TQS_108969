import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";

function TripDetails() {
  const { id } = useParams();
  const [showBuyTicketButton, setShowBuyTicketButton] = useState(false);
  const [buyTicketPrice, setBuyTicketPrice] = useState(0);
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [selectedSeatType, setSelectedSeatType] = useState(null);
  const [ticketAvailable, setTicketAvailable] = useState(false);

  const { isLoading, error, data } = useQuery({
    queryKey: ["tripDetails"],
    queryFn: async () => {
      const response = await fetch(`http://localhost:8080/trips/get?id=${id}`);
      if (!response.ok) throw new Error("Network response was not ok");
      const tripData = await response.json();
      return tripData;
    },
  });

  const trip = data;

  return (
    <div className="container mx-auto p-4">
      {isLoading && (
        <p className="text-lg text-center">Loading trip details...</p>
      )}
      {error && <p className="text-red-500 text-center">{error.message}</p>}

      {trip && (
        <div className="flex items-start justify-left">
          <div className="flex flex-col items-start w-1/2 space-y-6">
            <span className=" text-left space-y-4 text-6xl font-bold">
              Trip details for trip {trip.id}
            </span>
            <p className="mt-4 text-lg font-semibold">Origin:</p>
            <p className="text-gray-700">{trip.origin}</p>
            <p className="mt-4 text-lg font-semibold">Destination:</p>
            <p className="text-gray-700">{trip.destination}</p>
            <p className="mt-4 text-lg font-semibold">Date:</p>
            <p className="text-gray-700">{trip.date}</p>
            <p className="mt-4 text-lg font-semibold">Time:</p>
            <p className="text-gray-700">{trip.time}</p>
            <p className="mt-4 text-lg font-semibold">Bus Number:</p>
            <p className="text-gray-700">{trip.busID}</p>
          </div>
          {showBuyTicketButton && (
            <div className="space-y-4 pt-20">
              <p className="text-4xl"> Selected seat : {selectedSeat} </p>
              <p className="text-4xl"> Seat Type : {selectedSeatType}</p>
              <p className="text-4xl"> Price : ${buyTicketPrice}</p>

              {ticketAvailable ? (
                <button className="btn btn-info px-4 py-2 rounded-md">
                  Buy Ticket - ${buyTicketPrice}
                </button>
              ) : (
                <button className="btn btn-error px-4 py-2 rounded-md">
                  Seat Taken
                </button>
              )}
            </div>
          )}

          <div className=" ml-24 flex flex-col  w-1/2 justify-items-center space-y-4">
            <h1 className="text-6xl font-bold"> Bus Mapping </h1>

            <div className="grid grid-cols-4 gap-2">
              {trip.seats.map((seat) => (
                <button
                  key={seat.number}
                  className={`
      px-2 py-1 rounded-md text-center flex justify-center items-center
      ${seat.taken ? "btn-error" : "btn-success"}
    `}
                  onClick={() => {
                    const price =
                      seat.seatType === "normal"
                        ? trip.price
                        : Number(trip.price * 1.35).toFixed(2);
                    setShowBuyTicketButton(true);
                    setBuyTicketPrice(price);
                    setSelectedSeat(seat.number);
                    setSelectedSeatType(seat.seatType);
                    setTicketAvailable(!seat.taken);
                  }}
                >
                  {seat.number} - {seat.seatType}
                </button>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default TripDetails;
