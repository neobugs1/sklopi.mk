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
  { key: "name", label: "Chipsets", type: "checkbox" },
  { key: "socket", label: "Socket", type: "checkbox" },
  { key: "supportedMemory", label: "Supported Memory", type: "checkbox" },
  { key: "formFactor", label: "Form Factor", type: "checkbox" },
];

const ramFilterConfig = [
  { key: "name", label: "Type", type: "checkbox" },
  { key: "capacity", label: "Size", type: "checkbox" },
  { key: "speed", label: "Frequency", type: "checkbox" },
];

const caseFilterConfig = [
  { key: "name", label: "Type", type: "checkbox" },
  { key: "formFactor", label: "Size", type: "checkbox" },
];

const cpuCoolerFilterConfig = [
  { key: "name", label: "Type", type: "checkbox" },
  { key: "coolerType", label: "Cooler Type", type: "checkbox" },
];

const storageFilterConfig = [
  { key: "name", label: "Type", type: "checkbox" },
  { key: "capacity", label: "Size", type: "checkbox" },
  { key: "formFactor", label: "FormFactor", type: "checkbox" },
  { key: "type", label: "Type", type: "checkbox" },
];

const psuFilterConfig = [
  { key: "name", label: "Type", type: "checkbox" },
  { key: "wattage", label: "Wattage", type: "checkbox" },
  { key: "efficiencyRating", label: "Efficiency Rating", type: "checkbox" },
];
const cpuFilterConfig = [
  { key: "name", label: "Type", type: "checkbox" },
  { key: "brand", label: "Brand", type: "checkbox" },
  { key: "totalCores", label: "Total Cores", type: "range", minKey: "minTotalCores", maxKey: "maxTotalCores" },
  { key: "socket", label: "Socket", type: "checkbox" },
  { key: "boostClock", label: "Boost Clock", type: "range", minKey: "minBoostClock", maxKey: "maxBoostClock" },
  { key: "tdp", label: "TDP", type: "range", minKey: "minTdp", maxKey: "maxTdp" },
];
const gpuFilterConfig = [
  { key: "name", label: "Name", type: "checkbox" },
  { key: "brand", label: "Brand", type: "checkbox" },
  { key: "memorySize", label: "VRAM", type: "range", minKey: "minMemorySize", maxKey: "maxMemorySize" },
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
          <Route
            path="/gpu/products"
            element={<DynamicPart partType="GPU" apiEndpoint="http://localhost:8080/api/gpus/products" filterConfig={gpuFilterConfig} />}
          />
        </Routes>
      </Flex>
    </Flex>
    <Footer />
  </Box>
);

export default App;
