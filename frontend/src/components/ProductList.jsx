import React, { useState, useEffect } from "react";
import { Box, VStack, HStack, Text, Select, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import ProductItem from "./ProductItem";
import Pagination from "./Pagination";

const ProductList = ({ filters, apiEndpoint, partType }) => {
  const getDefaultSort = (partType) => {
    if (partType === "GPU") {
      return "performanceValue";
    } else if (partType === "CPU") {
      return "singleCoreValue";
    } else {
      return "price";
    }
  };

  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [sort, setSort] = useState(getDefaultSort(partType));

  useEffect(() => {
    fetchProducts();
  }, [currentPage, sort, filters]);

  const fetchProducts = async () => {
    const queryParams = new URLSearchParams();
    queryParams.append("page", currentPage);
    queryParams.append("size", 10);
    queryParams.append("sortBy", sort);
    queryParams.append("minPrice", filters.priceRange[0]);
    queryParams.append("maxPrice", filters.priceRange[1]);

    // Append range filters
    Object.keys(filters).forEach((key) => {
      if (Array.isArray(filters[key])) {
        filters[key].forEach((value) => queryParams.append(key, value));
      } else if (key.startsWith("min") || key.startsWith("max")) {
        queryParams.append(key, filters[key]);
      }
    });

    const response = await fetch(`${apiEndpoint}?${queryParams.toString()}`);
    const data = await response.json();
    setProducts(data.products);
    setTotalPages(data.totalPages);
  };

  const getHeaders = () => {
    switch (partType) {
      case "Motherboards":
        return (
          <>
            <Th>Socket</Th>
            <Th>Form Factor</Th>
            <Th>Supported Memory</Th>
          </>
        );
      case "RAM":
        return (
          <>
            <Th>Type</Th>
            <Th>Size</Th>
            <Th>Frequency</Th>
          </>
        );
      case "Case":
        return (
          <>
            <Th>Type</Th>
            <Th>Size</Th>
          </>
        );
      case "CPU Cooler":
        return (
          <>
            <Th>Brand</Th>
            <Th>Type</Th>
          </>
        );
      case "Storage":
        return (
          <>
            <Th>Brand</Th>
            <Th>Size</Th>
            <Th>Form Factor</Th>
          </>
        );
      case "PSU":
        return (
          <>
            <Th>Wattage</Th>
            <Th>Efficiency Rating</Th>
          </>
        );
      case "CPU":
        return (
          <>
            <Th>Type</Th>
            <Th>Cores</Th>
            <Th>Brand</Th>
            <Th>Socket</Th>
            <Th>Boost Clock</Th>
            <Th>TDP</Th>
          </>
        );
      case "GPU":
        return (
          <>
            <Th>Name</Th>
            <Th>Brand</Th>
            <Th>Memory</Th>
            <Th>VALUE</Th>
          </>
        );
      // Add other part types here as needed
      default:
        return null;
    }
  };

  return (
    <Box flex="1">
      <VStack align="start" spacing={4}>
        <HStack justify="space-between" w="full">
          <Text fontSize="2xl" color="var(--secondary-color)">
            {partType}
          </Text>
          <HStack>
            <Text>Sort by:</Text>
            <Select id="sort" value={sort} onChange={(e) => setSort(e.target.value)}>
              {partType === "GPU" && <option value="performanceValue">Value</option>}
              {partType === "CPU" && <option value="singleCoreValue">Value (Single Core)</option>}
              {partType === "CPU" && <option value="multiCoreValue">Value (Multi Core)</option>}
              {partType === "PSU" && <option value="efficiencyRating">Efficiency Rating (80+ Gold first)</option>}
              <option value="price asc">Price (Lowest first)</option>
              <option value="price desc">Price (Highest first)</option>
              <option value="name">Name (A-Z)</option>
            </Select>
          </HStack>
        </HStack>
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th></Th>
              <Th>Name</Th>
              {getHeaders()}
              <Th>Продавач</Th>
              <Th>Price</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {products.map((product) => (
              <ProductItem key={product.id} product={product} partType={partType} />
            ))}
          </Tbody>
        </Table>
        <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={setCurrentPage} />
      </VStack>
    </Box>
  );
};

export default ProductList;
