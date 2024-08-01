import { Box, Flex } from "@chakra-ui/react";
import { Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Nav from "./components/Nav";
import Footer from "./components/Footer";
import Builder from "./components/Builder";
import Home from "./components/Home";
import Motherboards from "./components/Motherboards";
import DynamicPart from "./components/DynamicPart";

const motherboardFilterConfig = [
  { key: "name", label: "Chipsets" },
  { key: "socket", label: "Socket" },
  { key: "supportedMemory", label: "Supported Memory" },
  { key: "formFactor", label: "Form Factor" },
];

const ramFilterConfig = [
  { key: "name", label: "Type" },
  { key: "capacity", label: "Size" },
  { key: "speed", label: "Frequency" },
];

const App = () => (
  <Box>
    <Header />
    <Nav />
    <Flex bg={"gray.700"} flexDir={"column"} pb={8}>
      <Flex flexDir={"column"} w={"80%"} mx={"auto"} mt={8}>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/builder" element={<Builder />} />
          <Route
            path="/motherboards/products"
            element={
              <DynamicPart partType="Motherboards" apiEndpoint="http://localhost:8080/api/products/motherboards" filterConfig={motherboardFilterConfig} />
            }
          />
          <Route
            path="/ram/products"
            element={<DynamicPart partType="RAM" apiEndpoint="http://localhost:8080/api/products/ram" filterConfig={ramFilterConfig} />}
          />
        </Routes>
      </Flex>
    </Flex>
    <Footer />
  </Box>
);

export default App;
