import { useNavigate } from "react-router-dom";
function Home() {
  const navigate = useNavigate();

  const goToTripPage = () => {
    console.log("Go to trip page");
    navigate("/trips");
  };

  const goToTicketsPage = () => {
    console.log("Go to tickets page");
    navigate("/tickets");
  };

  return (
    <div className="space-y-6 space-x-6">
      <h1>Bus Ticket Service </h1>
      <button className=" btn btn-success" onClick={goToTripPage}>
        {" "}
        Search for Trips{" "}
      </button>
      <button className="  btn btn-success" onClick={goToTicketsPage}>
        {" "}
        Check Ticket Reservations{" "}
      </button>
    </div>
  );
}

export default Home;
