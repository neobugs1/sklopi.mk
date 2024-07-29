import { Box, Flex } from "@chakra-ui/react";
import { Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Nav from "./components/Nav";
import Hero from "./components/Hero";
import FeaturedParts from "./components/FeaturedParts";
import Footer from "./components/Footer";

const App = () => (
  <Box>
    <Header />
    <Nav />
    <Flex bg={"gray.700"} flexDir={"column"} pb={8}>
      <Flex flexDir={"column"} w={"80%"} mx={"auto"} mt={8}>
        <Routes>
          <Route path="/" element={<Hero />} />
          {/* Placeholder for future pages */}
          <Route path="/builder" element={<div>System Builder</div>} />
          <Route path="/products" element={<div>Browse Products</div>} />
          <Route path="/builds" element={<div>Completed Builds</div>} />
          <Route path="/guide" element={<div>Build Guides</div>} />
        </Routes>
      </Flex>
    </Flex>
    <Footer />
  </Box>
);

export default App;
