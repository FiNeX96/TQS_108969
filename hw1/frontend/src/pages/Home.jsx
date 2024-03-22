
import { useNavigate } from "react-router-dom";
function Home() {

    const navigate = useNavigate();

    const goToTripPage = () => {
        console.log("Go to trip page");
        navigate("/trips");
    }

  return (
    <div >
      <h1 className="space-y-5">Bus Ticket Service </h1>
      <button onClick={goToTripPage}> Search for Trips </button>
    </div>
  );
}

export default Home;