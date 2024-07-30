import ProductList from "./ProductList";
import Sidebar from "./Sidebar";

const Motherboards = () => {
  return (
    <div className="container" style={{ display: "flex", maxWidth: "1200px", margin: "0 auto", padding: "20px" }}>
      <Sidebar />
      <ProductList />
    </div>
  );
};

export default Motherboards;
