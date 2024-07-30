import React from "react";
import { Box, Heading, Select, Input, VStack } from "@chakra-ui/react";

const Sidebar = () => (
  <aside className="sidebar" style={{ width: "250px", marginRight: "20px" }}>
    <Box bg="var(--filter-bg)" borderRadius="8px" p="15px" mb="20px">
      <Heading as="h3" color="var(--secondary-color)" size="md" mb="10px">
        Филтри
      </Heading>
      <VStack spacing={4}>
        <Box className="filter-option">
          <label htmlFor="manufacturer">Производител</label>
          <Select id="manufacturer">
            <option value="">Сите</option>
            <option value="asus">ASUS</option>
            <option value="msi">MSI</option>
            <option value="gigabyte">Gigabyte</option>
            <option value="asrock">ASRock</option>
          </Select>
        </Box>
        <Box className="filter-option">
          <label htmlFor="socket">Процесорско Лежиште</label>
          <Select id="socket">
            <option value="">Сите</option>
            <option value="am4">AM4</option>
            <option value="lga1200">LGA1200</option>
            <option value="lga1700">LGA1700</option>
          </Select>
        </Box>
        <Box className="filter-option">
          <label htmlFor="form-factor">Форм Фактор</label>
          <Select id="form-factor">
            <option value="">Сите</option>
            <option value="atx">ATX</option>
            <option value="matx">Micro ATX</option>
            <option value="itx">Mini ITX</option>
          </Select>
        </Box>
        <Box className="filter-option">
          <label htmlFor="max-price">Максимална Цена</label>
          <Input type="text" id="max-price" placeholder="Внесете максимална цена" />
        </Box>
      </VStack>
    </Box>
    <Box bg="var(--filter-bg)" borderRadius="8px" p="15px">
      <Heading as="h3" color="var(--secondary-color)" size="md" mb="10px">
        Зачувани Филтри
      </Heading>
      <VStack spacing={4}>
        <Box className="filter-option">
          <label>
            <Input type="checkbox" /> Ryzen Mini ITX
          </label>
        </Box>
        <Box className="filter-option">
          <label>
            <Input type="checkbox" /> Intel Productivity
          </label>
        </Box>
        <Box className="filter-option">
          <label>
            <Input type="checkbox" /> No-Budget Dream
          </label>
        </Box>
      </VStack>
    </Box>
  </aside>
);

export default Sidebar;
