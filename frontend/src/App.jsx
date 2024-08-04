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

const caseFilterConfig = [
  { key: "name", label: "Type" },
  { key: "formFactor", label: "Size" },
];

const cpuCoolerFilterConfig = [
  { key: "name", label: "Type" },
  { key: "coolerType", label: "Cooler Type" },
];

const storageFilterConfig = [
  { key: "name", label: "Type" },
  { key: "capacity", label: "Size" },
  { key: "formFactor", label: "FormFactor" },
  { key: "type", label: "Type" },
];

const psuFilterConfig = [
  { key: "name", label: "Type" },
  { key: "wattage", label: "Wattage" },
  { key: "efficiencyRating", label: "Efficiency Rating" },
];
const cpuFilterConfig = [
  { key: "name", label: "Type" },
  { key: "brand", label: "Brand" },
  { key: "totalCores", label: "Total Cores" },
  { key: "socket", label: "Socket" },
  { key: "boostClock", label: "Boost Clock" },
  { key: "tdp", label: "TDP" },
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
          <Route
            path="/case/products"
            element={<DynamicPart partType="Case" apiEndpoint="http://localhost:8080/api/products/cases" filterConfig={caseFilterConfig} />}
          />
          <Route
            path="/cpu-coolers/products"
            element={<DynamicPart partType="CPU Cooler" apiEndpoint="http://localhost:8080/api/products/cpu-coolers" filterConfig={cpuCoolerFilterConfig} />}
          />
          <Route
            path="/storage/products"
            element={<DynamicPart partType="Storage" apiEndpoint="http://localhost:8080/api/products/storage" filterConfig={storageFilterConfig} />}
          />
          <Route
            path="/psu/products"
            element={<DynamicPart partType="PSU" apiEndpoint="http://localhost:8080/api/psus/products" filterConfig={psuFilterConfig} />}
          />
          <Route
            path="/cpu/products"
            element={<DynamicPart partType="CPU" apiEndpoint="http://localhost:8080/api/cpus/products" filterConfig={cpuFilterConfig} />}
          />
        </Routes>
      </Flex>
    </Flex>
    <Footer />
  </Box>
);

export default App;
