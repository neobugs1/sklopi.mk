import React, { useState, useEffect } from "react";
import { Flex } from "@chakra-ui/react";
import Sidebar from "./Sidebar";
import ProductList from "./ProductList";

const DynamicPart = ({ partType, apiEndpoint, filterConfig }) => {
  const [filters, setFilters] = useState({
    name: [],
    priceRange: [0, 200000],
    ...filterConfig.reduce((acc, { key }) => ({ ...acc, [key]: [] }), {}),
    ...filterConfig.reduce((acc, { key, minKey, maxKey }) => {
      if (minKey && maxKey) {
        acc[minKey] = 0;
        acc[maxKey] = 10000000;
      }
      return acc;
    }, {}),
  });

  const [distinctFilters, setDistinctFilters] = useState({
    minPrice: 0,
    maxPrice: 10000,
    ...filterConfig.reduce(
      (acc, { key }) => ({
        ...acc,
        [`distinct${key[0].toUpperCase() + key.slice(1)}`]: [],
        [`min${key[0].toUpperCase() + key.slice(1)}`]: 0,
        [`max${key[0].toUpperCase() + key.slice(1)}`]: 0,
      }),
      {}
    ),
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
        (acc, { key }) => ({
          ...acc,
          [`distinct${key[0].toUpperCase() + key.slice(1)}`]: data[`distinct${key[0].toUpperCase() + key.slice(1)}`] || [],
          [`min${key[0].toUpperCase() + key.slice(1)}`]: data[`min${key[0].toUpperCase() + key.slice(1)}`] || 0,
          [`max${key[0].toUpperCase() + key.slice(1)}`]: data[`max${key[0].toUpperCase() + key.slice(1)}`] || 0,
        }),
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
