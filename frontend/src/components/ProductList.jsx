import React, { useState, useEffect } from "react";
import { Box, VStack, HStack, Text, Select, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import ProductItem from "./ProductItem";
import Pagination from "./Pagination";

const ProductList = ({ filters, apiEndpoint, partType }) => {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [sort, setSort] = useState("price");

  useEffect(() => {
    fetchProducts();
  }, [currentPage, sort, filters]);

  const fetchProducts = async () => {
    const response = await fetch(
      `${apiEndpoint}?page=${currentPage}&size=10&minPrice=${filters.priceRange[0]}&maxPrice=${filters.priceRange[1]}&sortBy=${sort}` +
        Object.keys(filters)
          .filter((key) => key !== "priceRange")
          .map((key) => `&${key}=${filters[key].join(",")}`)
          .join("")
    );
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
