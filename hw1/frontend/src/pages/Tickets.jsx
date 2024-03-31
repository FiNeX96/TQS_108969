import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useQuery } from "@tanstack/react-query";

function Tickets() {
  const navigate = useNavigate();
  const [showAll, setShowAll] = useState(false);


  const getTickets = useQuery({
    queryKey: ["tickets"],
    queryFn: async () => {
      let response = await fetch("http://localhost:8080/tickets/list");
      if (!response.ok) throw new Error("Network response was not ok");
      response = await response.json();
      response.sort((a, b) => a.tripID - b.tripID);
      return response;
    },
  });

  let {
    isLoading: ticketsLoading,
    error: ticketsError,
    data: ticketsData,
  } = getTickets;

  const goBack = () => {
    navigate(-1);
  };

  const seeAll = () => {
    setShowAll(true);
  };

    const searchByTicketID = (e) => {
    const ticketID = e.target.value;
    if (ticketID === "") {
      setShowAll(true);
    } else {
      setShowAll(false);
      const filteredData = ticketsData.filter(
        (ticket) => ticket.id === ticketID
      );
      if (filteredData.length > 0) {
        ticketsData = filteredData;
        setShowAll(true);
      } else {
        ticketsData = [];
      }
    }
};

  return (
    <>
      <button onClick={seeAll}> See All</button>

      <input
        type="text"
        className="bg-black text-white p-2 rounded-md w-1/4 mt-4"
        placeholder="Search by Ticket ID"
        onChange={searchByTicketID}
      />

      <button onClick={goBack}>Go back</button>

      {showAll && (
        <div className="ticket-reservations flex flex-col justify-center items-center space-y-5">
          {ticketsLoading && <p className="text-gray-600">Loading...</p>}
          {ticketsError && (
            <p className="text-red-500">Error: {ticketsError.message}</p>
          )}
          {ticketsData && (
            <div className="ticket-list bg-gray-100 rounded-lg p-4 shadow-md">
              <h2 className="text-2xl font-semibold mb-2 text-neutral-950">
                Your Ticket Reservations:
              </h2>
              <table className="table-auto w-full">
                <thead>
                  <tr className="text-left border-b border-gray-200">
                    <th className="text-gray-600 font-medium px-4 py-2">
                      Trip ID
                    </th>
                    <th className="text-gray-600 font-medium px-4 py-2">
                      Seat Number
                    </th>
                    <th className="text-gray-600 font-medium px-4 py-2">
                      Price
                    </th>
                    <th className="text-gray-600 font-medium px-4 py-2">
                      Origin
                    </th>
                    <th className="text-gray-600 font-medium px-4 py-2">
                      Destination
                    </th>
                    <th className="text-gray-600 font-medium px-4 py-2">
                      Date
                    </th>
                    <th className="text-gray-600 font-medium px-4 py-2">
                      Time
                    </th>
                    <th className="text-gray-600 font-medium px-4 py-2">
                        Ticket ID 
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {ticketsData.map((ticket) => (
                    <tr key={ticket.id} className="border-b border-gray-200">
                      <td className="text-gray-700 px-4 py-2">
                        {ticket.tripID}
                      </td>
                      <td className="text-gray-700 px-4 py-2">
                        {ticket.seatNumber}
                      </td>
                      <td className="text-gray-700 px-4 py-2">
                        {ticket.price}
                      </td>
                      <td className="text-gray-700 px-4 py-2">
                        {ticket.origin}
                      </td>
                      <td className="text-gray-700 px-4 py-2">
                        {ticket.destination}
                      </td>
                      <td className="text-gray-700 px-4 py-2">{ticket.date}</td>
                      <td className="text-gray-700 px-4 py-2">{ticket.time}</td>
                       <td className="text-gray-700 px-4 py-2">{ticket.id}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}
    </>
  );
}

export default Tickets;
