import React, { useState, useEffect } from "react";
import { Box, Table, Thead, Tbody, Tr, Th, Td, Select, Text, VStack, HStack } from "@chakra-ui/react";
import ProductItem from "./ProductItem";
import Pagination from "./Pagination";

const ProductList = ({ filters }) => {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [sort, setSort] = useState("price");

  useEffect(() => {
    fetchProducts();
  }, [currentPage, sort, filters]);

  const fetchProducts = async () => {
    const response = await fetch(
      `http://localhost:8080/api/products/motherboards?page=${currentPage}&size=10&minPrice=${filters.priceRange[0]}&maxPrice=${
        filters.priceRange[1]
      }&sortBy=${sort}&name=${filters.chipset.join(",")}&socket=${filters.socket.join(",")}&supportedMemory=${filters.supportedMemory.join(
        ","
      )}&formFactor=${filters.formFactor.join(",")}`
    );
    const data = await response.json();

    setProducts(data.products); // Adjusted to match the API response format
    setTotalPages(data.totalPages);
  };

  return (
    <Box flex="1">
      <VStack align="start" spacing={4}>
        <HStack justify="space-between" w="full">
          <Text fontSize="2xl" color="var(--secondary-color)">
            Матични Плочи
          </Text>
          <HStack>
            <Text>Подреди по:</Text>
            <Select id="sort" value={sort} onChange={(e) => setSort(e.target.value)}>
              <option value="price asc">Цена (Најниска прво)</option>
              <option value="price desc">Оценка (Највисока прво)</option>
              <option value="name">Име (А-Ш)</option>
              <option value="name">Име (А-Ш)</option>
            </Select>
          </HStack>
        </HStack>
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th></Th>
              <Th>Име</Th>
              <Th>Лежиште</Th>
              <Th>Форм Фактор</Th>
              <Th>Меморија Поддржана</Th>
              <Th>Цена</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {products.map((product) => (
              <ProductItem key={product.id} product={product} />
            ))}
          </Tbody>
        </Table>
        <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={setCurrentPage} />
      </VStack>
    </Box>
  );
};

export default ProductList;
