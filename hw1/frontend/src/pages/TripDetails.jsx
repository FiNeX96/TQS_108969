import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";

function TripDetails() {
  const { id } = useParams();
  const [showBuyTicketButton, setShowBuyTicketButton] = useState(false);
  const [buyTicketPrice, setBuyTicketPrice] = useState(0);
  const [selectedCurrency, setSelectedCurrency] = useState("EUR");
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [selectedSeatType, setSelectedSeatType] = useState(null);
  const [ticketAvailable, setTicketAvailable] = useState(false);
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [email, setEmail] = useState("");
  const [ticketInfo, setTicketInfo] = useState(false);
  const [ticketID, setTicketID] = useState(null);
  const navigate = useNavigate();
  const targetRef = useRef(null);

  const {
    isLoading,
    error,
    data: trip,
  } = useQuery({
    queryKey: ["tripDetails", selectedCurrency],
    queryFn: async () => {
      const response = await fetch(
        `http://localhost:8080/trips/get?id=${id}&currency=${selectedCurrency}`
      );

      if (!response.ok) throw new Error("Network response was not ok");
      const tripData = await response.json();
      setBuyTicketPrice(tripData.price);
      return tripData;
    },
  });

  const fetchData = async (url) => {
    const response = await fetch(url);
    if (!response.ok) throw new Error("Network response was not ok");
    return await response.json();
  };

  const getCurrencies = useQuery({
    queryKey: ["currencies"],
    queryFn: async () =>
      await fetchData("http://localhost:8080/currencies/list"),
  });

  const {
    isLoading: currenciesLoading,
    error: currenciesError,
    data: currenciesData,
  } = getCurrencies;

  const buyTicket = async () => {
    if (!name || !phone || !email) {
      alert("Please fill in all the fields");
      return;
    }

    if (phone && !/^\d+$/.test(phone)) {
      alert("Phone number must contain only digits");
      return;
    }

    if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      alert("Invalid email address");
      return;
    }

    let data = {
      tripID: id,
      seatNumber: selectedSeat,
      price: `${buyTicketPrice} ${selectedCurrency}`,
      name: name,
      phone: phone,
      email: email,
    };

    const response = await fetch("http://localhost:8080/tickets/buy", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    let json_response = await response.json();

    if (!response.ok) {
      alert("Error buying ticket -> " + json_response.message); // Access the message property from the parsed JSON
      return;
    }

    setTicketInfo(true);
    setTicketID(json_response.id);
  };

  const goBack = () => {
    navigate(-1);
  };

  return (
    <>
      <button onClick={goBack}>Go back</button>
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
              <span>Select Currency</span>
              <select
                className="select bg-grey select-success w-max max-w-xs"
                value={selectedCurrency}
                onChange={(e) => setSelectedCurrency(e.target.value)}
              >
                {currenciesData &&
                  currenciesData.length > 0 &&
                  currenciesData.map((currency) => (
                    <option key={currency} value={currency}>
                      {currency}
                    </option>
                  ))}
              </select>
            </div>

            <div className=" ml-24 flex flex-col  w-1/2 justify-items-center space-y-4">
              <h1 className="text-6xl font-bold"> Bus Mapping </h1>

              <div className="grid grid-cols-4 gap-2">
                {trip.seats.map((seat) => (
                  <button
                    key={seat.number}
                    className={`
                  btn 
                  px-2 py-1 rounded-md text-center flex justify-center items-center
                  ${seat.taken ? "btn-error" : "btn-success"}
                `}
                    onClick={() => {
                      targetRef.current?.scrollIntoView({ behavior: 'smooth' });
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
        {selectedSeat && (
          <div>
            <div className="space-y-4 pt-20">
              <p className="text-4xl"> Selected seat : {selectedSeat} </p>
              <p className="text-4xl"> Seat Type : {selectedSeatType}</p>
              <p className="text-4xl">
                {" "}
                Price : {Number(buyTicketPrice).toFixed(2)} {selectedCurrency}
              </p>

              {ticketAvailable ? (
                <button
                  className="btn btn-info px-4 py-2 rounded-md"
                  onClick={buyTicket}
                >
                  Buy Ticket - {Number(buyTicketPrice).toFixed(2)}{" "}
                  {selectedCurrency}
                </button>
              ) : (
                <button className="btn btn-error px-4 py-2 rounded-md disabled" >
                  Seat Taken
                </button>
              )}
            </div>

            <div className="flex justify-center items-center space-x-4 mt-16" ref={targetRef}>
              Customer Details
              <div className="w-full">
                <label className="text-gray-700 font-medium block mb-2">
                  Name:
                </label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  className=" appearance-none bg-white border rounded-md py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
                  onChange={(e) => setName(e.target.value)}
                />
              </div>
              <div className="w-full">
                <label className="text-gray-700 font-medium block mb-2">
                  Phone Number:
                </label>
                <input
                  type="tel"
                  id="phone"
                  name="phone"
                  className="appearance-none bg-white border rounded-md py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
                  onChange={(e) => setPhone(e.target.value)}
                />
              </div>
              <div className="w-full">
                <label className="text-gray-700 font-medium block mb-2">
                  Email:
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  className="appearance-none bg-white border rounded-md py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
            </div>
          </div>
        )}
        {ticketInfo && (
          <div className="mt-8">
            <p className="text-2xl">
              Ticket bought successfully. Your ticket ID is {ticketID}
            </p>
          </div>
        )}
      </div>
    </>
  );
}

export default TripDetails;
