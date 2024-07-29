import { Box, Flex } from "@chakra-ui/react";
import { Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Nav from "./components/Nav";
import Footer from "./components/Footer";
import Builder from "./components/Builder";
import Home from "./components/Home";

const App = () => (
  <Box>
    <Header />
    <Nav />
    <Flex bg={"gray.700"} flexDir={"column"} pb={8}>
      <Flex flexDir={"column"} w={"80%"} mx={"auto"} mt={8}>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/builder" element={<Builder />} />
          {/* <Route path="/products" element={<div>Browse Products</div>} />
          <Route path="/builds" element={<div>Completed Builds</div>} />
          <Route path="/guide" element={<div>Build Guides</div>} /> */}
        </Routes>
      </Flex>
    </Flex>
    <Footer />
  </Box>
);

export default App;
