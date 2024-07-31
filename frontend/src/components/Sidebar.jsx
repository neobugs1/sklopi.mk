import React, { useState, useEffect } from "react";
import { Box, Heading, Checkbox, VStack, RangeSlider, RangeSliderTrack, RangeSliderFilledTrack, RangeSliderThumb, Text, HStack } from "@chakra-ui/react";

const Sidebar = ({ filters, setFilters, minPrice, maxPrice, distinctSockets, distinctFormFactors, distinctSupportedMemory }) => {
  const [priceRange, setPriceRange] = useState([minPrice, maxPrice]);

  useEffect(() => {
    setPriceRange([minPrice, maxPrice]);
  }, [minPrice, maxPrice]);

  const handleCheckboxChange = (e) => {
    const { name, value, checked } = e.target;
    setFilters((prevFilters) => ({
      ...prevFilters,
      [name]: checked ? [...(prevFilters[name] || []), value] : (prevFilters[name] || []).filter((item) => item !== value),
    }));
  };

  const handlePriceChange = (value) => {
    setPriceRange(value);
    setFilters((prevFilters) => ({
      ...prevFilters,
      priceRange: value, // Update the priceRange in filters
    }));
  };

  return (
    <Box width="250px" marginRight="20px" bg="var(--filter-bg)" borderRadius="8px" p="15px">
      <Heading as="h3" color="var(--secondary-color)" size="md" mb="10px">
        Filters
      </Heading>

      <VStack spacing={4}>
        <Box>
          <Heading as="h4" size="sm">
            Form Factor
          </Heading>
          {distinctFormFactors.map((formFactor) => (
            <Checkbox key={formFactor} name="formFactor" value={formFactor} onChange={handleCheckboxChange}>
              {formFactor}
            </Checkbox>
          ))}
        </Box>

        <Box>
          <Heading as="h4" size="sm">
            Socket
          </Heading>
          {distinctSockets.map((socket) => (
            <Checkbox key={socket} name="socket" value={socket} onChange={handleCheckboxChange}>
              {socket}
            </Checkbox>
          ))}
        </Box>

        <Box>
          <Heading as="h4" size="sm">
            Supported Memory
          </Heading>
          {distinctSupportedMemory.map((memory) => (
            <Checkbox key={memory} name="supportedMemory" value={memory} onChange={handleCheckboxChange}>
              {memory}
            </Checkbox>
          ))}
        </Box>

        <Box>
          <Text mb={2}>Price:</Text>
          <RangeSlider min={minPrice} max={maxPrice} step={10} value={priceRange} onChange={handlePriceChange} aria-label={["min-price", "max-price"]}>
            <RangeSliderTrack>
              <RangeSliderFilledTrack />
            </RangeSliderTrack>
            <RangeSliderThumb index={0} />
            <RangeSliderThumb index={1} />
          </RangeSlider>
          <HStack justify="space-between" mt={2}>
            <Text>Min: {priceRange[0]}</Text>
            <Text>Max: {priceRange[1]}</Text>
          </HStack>
        </Box>
      </VStack>
    </Box>
  );
};

export default Sidebar;
