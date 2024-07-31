import React, { useState, useEffect } from "react";
import { HStack, Box, Flex } from "@chakra-ui/react";
import ProductList from "./ProductList";
import Sidebar from "./Sidebar";

const Motherboards = () => {
  const [filters, setFilters] = useState({
    name: [],
    socket: [],
    supportedMemory: [],
    formFactor: [],
    priceRange: [0, 200000], // Ensure this default range is set
  });
  const [distinctFilters, setDistinctFilters] = useState({
    minPrice: 0,
    maxPrice: 10000,
    distinctSockets: [],
    distinctFormFactors: [],
    distinctSupportedMemory: [],
  });

  useEffect(() => {
    fetchDistinctFilters();
  }, []);

  const fetchDistinctFilters = async () => {
    const response = await fetch("http://localhost:8080/api/products/motherboards");
    const data = await response.json();
    setDistinctFilters({
      minPrice: data.minPrice,
      maxPrice: data.maxPrice,
      distinctSockets: data.distinctSockets || [],
      distinctFormFactors: data.distinctFormFactors || [],
      distinctSupportedMemory: data.distinctSupportedMemory || [],
    });
  };

  return (
    <Flex>
      <Sidebar
        filters={filters}
        setFilters={setFilters}
        minPrice={distinctFilters.minPrice}
        maxPrice={distinctFilters.maxPrice}
        distinctSockets={distinctFilters.distinctSockets}
        distinctFormFactors={distinctFilters.distinctFormFactors}
        distinctSupportedMemory={distinctFilters.distinctSupportedMemory}
      />
      <ProductList filters={filters} />
    </Flex>
  );
};

export default Motherboards;
