import React, { useState, useEffect } from "react";
import {
  Flex,
  Box,
  VStack,
  HStack,
  Heading,
  Checkbox,
  Text,
  RangeSlider,
  RangeSliderTrack,
  RangeSliderFilledTrack,
  RangeSliderThumb,
  Select,
  Table,
  Thead,
  Tr,
  Th,
  Tbody,
} from "@chakra-ui/react";
import ProductItem from "./ProductItem";
import Pagination from "./Pagination";
import Sidebar from "./Sidebar";
import ProductList from "./ProductList";

const DynamicPart = ({ partType, apiEndpoint, filterConfig }) => {
  const [filters, setFilters] = useState({
    name: [],
    priceRange: [0, 200000],
    ...filterConfig.reduce((acc, { key }) => ({ ...acc, [key]: [] }), {}),
  });

  const [distinctFilters, setDistinctFilters] = useState({
    minPrice: 0,
    maxPrice: 10000,
    ...filterConfig.reduce((acc, { key }) => ({ ...acc, [`distinct${key[0].toUpperCase() + key.slice(1)}`]: [] }), {}),
  });

  useEffect(() => {
    fetchDistinctFilters();
  }, []);

  const fetchDistinctFilters = async () => {
    const response = await fetch(apiEndpoint);
    const data = await response.json();
    setDistinctFilters({
      minPrice: data.minPrice,
      maxPrice: data.maxPrice,
      ...filterConfig.reduce(
        (acc, { key }) => ({ ...acc, [`distinct${key[0].toUpperCase() + key.slice(1)}`]: data[`distinct${key[0].toUpperCase() + key.slice(1)}`] || [] }),
        {}
      ),
    });
  };

  return (
    <Flex>
      <Sidebar filters={filters} setFilters={setFilters} distinctFilters={distinctFilters} filterConfig={filterConfig} />
      <ProductList filters={filters} apiEndpoint={apiEndpoint} partType={partType} />
    </Flex>
  );
};

export default DynamicPart;
